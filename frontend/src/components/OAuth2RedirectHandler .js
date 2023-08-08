import { useEffect, useState } from "react";
import { setCandidate } from "../store/storage";
export default function OAuth2RedirectHandler() {
  // let getUrlParameter =  (name) => {
  //     name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
  //     var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');

  //     var results = regex.exec(this.props.location.search);
  //     return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
  // };
  const [candidateState, setCandidateState] = useState(false);

  useEffect(() => {
    const urlSearchParams = new URLSearchParams(window.location.search);
    const token = urlSearchParams.get("token");
    setCandidate({ token: token });
    setCandidateState(true);

    const closePageAfterDelay = () => {
      window.close();
    };

    const delayInMilliseconds = 1000; 
    const timeoutId = setTimeout(closePageAfterDelay, delayInMilliseconds);

    // useEffect'un temizleme fonksiyonu ile, bileşen kaldırıldığında timeout'u iptal ederiz.
    return () => clearTimeout(timeoutId);
  }, []);

  // const urlSearchParams = new URLSearchParams(window.location.search);
  // const code = urlSearchParams.get('code');
  // const state = urlSearchParams.get('state');
  // debugger;
  // async function getAccessToken(code, state) {
  //     const response = await axios.get(`http://localhost:8080/api/candidate/v1/linkedin?code=${code}&state=${state}`)
  //     .then((response) => {
  //         debugger;
  //         console.log(response);
  //     }).catch((error) => {
  //         debugger;
  //         console.log(error);
  //     });
  // }
  // useEffect(() => {
  //     getAccessToken(code, state);
  // }, []);

  return (
    <div className="flex items-center justify-center h-screen">
      {candidateState && (
        <div className="text-center">
          <h2 className="text-2xl font-bold text-green-500">Giriş Başarılı</h2>
        </div>
      )}
    </div>
  );
}
