window.onload = function() {

    document.querySelector("#submit-button").onclick = function() {
        const name = document.querySelector("#name-input").value;
        document.querySelector("#message-div").innerHTML = "Hello " + name + "!";
    }

}