import { backgroundHttp } from 'background-http';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    backgroundHttp.echo({ value: inputValue })
}
