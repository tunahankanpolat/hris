import { useLinkedIn } from "react-linkedin-login-oauth2";
import axios from "axios";
export default function LinkedinLoginButton() {
  function linkedInLogin(e) {
    e.preventDefault();
    var url = process.env.REACT_APP_CANDIDATE_AUTHORIZATION_URL;
    const options =
      "directories=no, titlebar=no, toolbar=no, location=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=800, height=600, left=300, top=100, cookieEnabled=false, clearsessioncache=true, clearcache=true";

    // window'a eventlistener ekle, başka bir componentte kapatılmasının sağladığından emin olun
    var popupWindow = window.open(encodeURI(url), "LinkedIn Login", options);
    var timer = setInterval(function() { 
      if(popupWindow.closed) {
        clearInterval(timer);
        window.location.href = "/candidate/me"; // Yönlendirme yap
      }
    }, 1000);
  }

  // const { linkedInLogin } = useLinkedIn({
  //   clientId: '77t7dcuw1iix7q',
  //   scope: 'r_liteprofile r_emailaddress w_member_social',
  //   redirectUri: `http://localhost:3000/oauth2/redirect`, // for Next.js, you can use `${typeof window === 'object' && window.location.origin}/linkedin`
  //   onSuccess: (code) => {
  //     console.log(code);
  //   },
  //   onError: (error) => {
  //     console.log(error);
  //   },
  // });

  // const linkedInLogin = async () => {
  //   await axios.get("http://localhost:8080/oauth2/authorization/linkedin?redirect_uri=http://localhost:3000/oauth2/redirect")
  //   .then((response) => {
  //       debugger;
  //       console.log(response);
  //   }).catch((error) => {
  //       debugger;
  //       console.log(error);
  //   });
  // }
  return (
    <button
      onClick={linkedInLogin}
      className="ml-8 px-6 py-3 rounded-full ring-[1px] text-blue-600  ring-blue-600"
    >
      İlana Başvurabilmek için Linkedin ile Oturum Açınız...
    </button>
  );
}
