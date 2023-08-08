export default function LinkedinLoginButton() {
  function linkedInLogin(e) {
    e.preventDefault();
    var url = process.env.REACT_APP_CANDIDATE_AUTHORIZATION_URL;
    const options =
      "directories=no, titlebar=no, toolbar=no, location=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=800, height=600, left=300, top=100, cookieEnabled=false, clearsessioncache=true, clearcache=true";
    var popupWindow = window.open(encodeURI(url), "LinkedIn Login", options);
    var timer = setInterval(function() { 
      if(popupWindow.closed) {
        clearInterval(timer);
        window.location.href = "/candidate/me";
      }
    }, 1000);
  }
  return (
    <button
      onClick={linkedInLogin}
      className="ml-8 px-6 py-3 rounded-full ring-[1px] text-blue-600  ring-blue-600"
    >
      İlana Başvurabilmek için Linkedin ile Oturum Açınız...
    </button>
  );
}
