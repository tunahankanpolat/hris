export default function LinkedinLoginButton() {
  function linkedInLogin(e) {
    e.preventDefault();
    var url = process.env.REACT_APP_CANDIDATE_LOGIN_URL;
    window.open(
      encodeURI(url),
      "LinkedIn Login",
      "width=800, height=600, left=300, top=100"
    );
  }

  return (
    <button
      onClick={linkedInLogin}
      className="ml-8 px-6 py-3 rounded-full ring-[1px] text-blue-600  ring-blue-600"
    >
      İlana Başvurabilmek için Linkedin ile Oturum Aç
    </button>
  );
}
