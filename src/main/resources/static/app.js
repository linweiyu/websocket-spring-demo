var stompClient = null;
var user;
var message = {};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function judgeIsConnect() {
    if (stompClient == null) {
        alert("You should connect first !@");
        return false;
    } else
        return true;
}

function judgeIsLogin() {
    if (user == null) {
        alert("You should log in first !@");
        return false;
    } else
        return true;
}

function connect() {
    var socket = new SockJS('/chatroom');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/chat', function (msg) {
            var response = JSON.parse(msg.body);
            switch (response.type) {
                case 201:
                    showMessage(response.body);
                    break;
                default:
                    break;
            }
        });
        stompClient.subscribe('/user/topic/user', function (msg) {
            var response = JSON.parse(msg.body);
            switch (response.type) {
                case 100:
                    user = response.body;
                    showGreeting(user.name);
                    break;
                case 101:
                    sayGoodBye(response.signal);
                    user = null;
                    break;
                default:
                    break;
            }
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    if(user != null){
        alert("You already log in");
        return;
    }
    if (judgeIsConnect())
        stompClient.send("/app/login", {}, JSON.stringify({'name': $("#name").val()}));
}

function logout() {
    if (judgeIsConnect())
        stompClient.send("/app/logout", {}, JSON.stringify(user))
}

function chat() {
    if (judgeIsConnect() && judgeIsLogin()) {
        message.message = htmlEntities($("#content").val());
        message.user = user;
        stompClient.send("/app/talk", {}, JSON.stringify(message));
    }
}

function showGreeting(message) {
    $("#greetings").append("<tr><td> Welcome " + message + " Coming ! </td></tr>");
}

function sayGoodBye(signal) {
    if (signal == 1)
        $("#greetings").append("<tr><td> Success Leave Chat Room ! </td></tr>");
    else
        $("#greetings").append("<tr><td> You are not log in ! </td></tr>");
}

function showMessage(message) {
    $("#greetings").append("<tr><td> " + message.user.name + " say :  " + message.message + "</td></tr>");
}

function htmlEntities(str) {
    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
    $("#logout").click(function () {
        logout();
    });
    $("#chat").click(function () {
        $(this).attr("disabled","true");
        chat();
        setTimeout("$('#chat').removeAttr('disabled')",10000);
    });
    $("#content").on("keydown", function (e) {
        if(e.which != 13)
            $('#chat').removeAttr('disabled');
    });
});