function checkRedirect(){
  var username = storage.get("username");
  if(typeof username == "undefined"){
    location.href = "login.html";
  }
  storage.set("receiver","undefined");
}

function printUsername(){
  var username = storage.get("username");
  document.write(username);
}

function logout(){
    storage.removeAll();
    location.href = "login.html";
}

function getProfile(){
  var username = storage.get("username");
  var token = storage.get("token");
  var requestJson = {"username":username,"token":token};

  var xhttp = new XMLHttpRequest();
  xhttp.withCredentials = true;

  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      var response = xhttp.responseText;
      var responseJSON = JSON.parse(response);
      storage.set("pseudonym",responseJSON.pseudonym);
      storage.set("contact",responseJSON.contact);

    }
  };

  xhttp.open("POST", "http://141.19.142.61:5002/profile", false);
  xhttp.setRequestHeader("Content-Type","application/json")
  var input = JSON.stringify(requestJson);
  xhttp.send(input);

}

function printReceiver(){
  var receivers = storage.get("contact");
  for (i in receivers){
    document.write("<li role='presentation'><a role='menuitem' tabindex='-1' option value=" + receivers[i] + " onclick='selectReceiver(this.innerHTML)'>" + receivers[i] + "</a></li>");
  }
}

function selectReceiver(receiver){
  storage.set("receiver",receiver);
  document.getElementById("dropdownReceiver").innerHTML = receiver;
}

function send(){
  var token = storage.get("token");
  var from = storage.get("pseudonym");
  var to = storage.get("receiver");

  if(typeof to !== "undefined"){
    var date = new Date().toISOString();
    var text = document.getElementById("message").value;

    if(text !== ""){
      var message = {"token":token,"from":from,"to":to,"date":date,"text":text};
      var xhttp = new XMLHttpRequest();
      xhttp.withCredentials = true;

      xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 201) {
          var response = xhttp.responseText;
          var responseJSON = JSON.parse(response);
          var messages = storage.get(to);

          if(typeof messages == "undefined"){
            messages = [];
          } else {
            messages = JSON.parse(messages);
          }
          messages.push(message);
          storage.set(to, JSON.stringify(messages));
        }
      };

      xhttp.open("PUT", "http://141.19.142.61:5000/send", false);
      xhttp.setRequestHeader("Content-Type","application/json");
      var input = JSON.stringify(message);
      xhttp.send(input);

    } else {
      document.getElementById("noMessage").innerHTML = "Bitte geben Sie eine Nachricht ein."
    }
  } else {
    document.getElementById("noReceiver").innerHTML = "Bitte waelen Sie einen Empfaenger aus."
  }
}

function getMessages(){
  var token = storage.get("token");
  var pseudonym = storage.get("pseudonym");
  var sequence = storage.get("receivedSequence");

  var xhttp = new XMLHttpRequest();
  xhttp.withCredentials = true;
  
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      var response = xhttp.responseText;
      var responseJSON = JSON.parse(response);

      var receiver;
      var messages;

      for(message in responseJSON){
        receiver = responseJSON[message].from;
        messages = storage.get(receiver);

        if((typeof messages) === "undefined") {
          messages = [];
        } else {
          messages = JSON.parse(messages);
        }

        messages.push(responseJSON[message]);
        storage.set(receiver, JSON.stringify(messages));
        storage.set("receivedSequence", responseJSON[message].sequence);
      }
    }
  };

  if(typeof sequence === "undefined"){
    xhttp.open("GET", "http://141.19.142.61:5000/messages/" + pseudonym, false);
  } else {
    xhttp.open("GET", "http://141.19.142.61:5000/messages/" + pseudonym + "/" + sequence, true);
  }
  xhttp.setRequestHeader("Accepts","application/json");
  xhttp.setRequestHeader("Authorization"," Token " + token);

  xhttp.send();
}

function printMessages(){
  var receiver = storage.get("receiver");
  var messages = storage.get(receiver);

  if(typeof messages !== "undefined"){
    messages = JSON.parse(messages);
    var date;
    var tmp = "";
    for(i in messages){
      date = new Date(messages[i].date);
      hours = date.getHours();
      minutes = date.getMinutes();

      if(hours < 10){
        hours = "0" + hours;
      }
      if(minutes < 10){
        minutes = "0" + minutes;
      }
      tmp = tmp + "<tr><td>" + messages[i].from + "</td><td>" + messages[i].to + "</td><td>" + messages[i].text + "</td><td>" + hours + ":" + minutes + "</td></tr>";
    }
    document.getElementById("messageTable").innerHTML = tmp;
  }
}
