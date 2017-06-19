function login(switchWindow){
  var username = document.getElementById("username").value;
  var password = document.getElementById("password").value;
  var requestJson = {"username":username,"password":password};

  var xhttp = new XMLHttpRequest();
  xhttp.withCredentials = true;

  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      var response = xhttp.responseText;
      var responseJSON = JSON.parse(response);
      storage.set("username",username);
      storage.set("token",responseJSON.token);
      storage.set("expireDate",responseJSON.expireDate);
      if(switchWindow == true){
        location.href = "messenger.html";
      }
    } else if (this.readyState == 4){
      document.getElementById("loginFail").innerHTML = "Bitte ueberpruefen Sie Ihre Eingaben";
    }
  };
  var loginService = storage.get("loginService");
  xhttp.open("POST", loginService + "/login", false);
  xhttp.setRequestHeader("Content-Type","application/json");
  var input = JSON.stringify(requestJson);
  xhttp.send(input);

}

function signup(){
  var username = document.getElementById("username").value;
  var pseudonym = document.getElementById("pseudonym").value;
  var password = document.getElementById("password").value;
  var requestJson = {"username":username,"pseudonym":pseudonym,"password":password};

  var xhttp = new XMLHttpRequest();
  xhttp.withCredentials = true;

  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      login(false);
      location.href = "messenger.html";
    } else if(this.readyState == 4){
      document.getElementById("loginFail").innerHTML = "Bitte ueberpruefen Sie Ihre Eingaben";
    }
  };

  var registrationService = storage.get("registrationService");
  xhttp.open("PUT", registrationService + "/register", false);
  xhttp.setRequestHeader("Content-Type","application/json");
  var input = JSON.stringify(requestJson);
  xhttp.send(input);

}
