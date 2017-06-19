function changeServer(){
  var registrationService = document.getElementById("registrationService").value;
  var loginService = document.getElementById("loginService").value;
  var messageService = document.getElementById("messageService").value;

  if(typeof registrationService !== "undefined"){
    storage.set("registrationService", registrationService);
  }
  if(typeof loginService !== "undefined"){
    storage.set("loginService", loginService);
  }
  if(typeof messageService !== "undefined"){
    storage.set("messageService", messageService);
  }

  setTimeout(function(){
    document.getElementById("status").innerHTML = "Server erfolgreich konfiguriert";
  }, 2000);

  location.href = "messenger.html";
}

function checkServerConfiguration(){
  var registrationService = storage.get("registrationService");
  var loginService = storage.get("loginService");
  var messageService = storage.get("messageService");

  if(typeof registrationService == "undefined"){
    storage.set("registrationService", "http://141.19.142.61:5002");
  }
  if(typeof loginService == "undefined"){
    storage.set("loginService", "http://141.19.142.61:5001");
  }
  if(typeof messageService == "undefined"){
    storage.set("messageService", "http://141.19.142.61:5000");
  }
}
