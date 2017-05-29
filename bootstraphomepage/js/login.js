function login(switchWindow){
  var username = document.getElementById("username").value;
  var password = document.getElementById("password").value;
  var requestJson = {"username":username,"password":password};

  var xhttp = new XMLHttpRequest();
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

  xhttp.open("POST", "http://141.19.142.61:5001/login", false);
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
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      login(false);
      location.href = "messenger.html";
    } else if(this.readyState == 4){
      document.getElementById("loginFail").innerHTML = "Bitte ueberpruefen Sie Ihre Eingaben";
    }
  };

  xhttp.open("PUT", "http://141.19.142.61:5002/register", false);
  xhttp.setRequestHeader("Content-Type","application/json");
  var input = JSON.stringify(requestJson);
  xhttp.send(input);

}
