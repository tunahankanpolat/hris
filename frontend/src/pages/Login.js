import React, { useState } from "react";
import LoginNavBar from "../components/LoginNavBar";
import { toast } from 'react-toastify';
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import HumanResourceService from "../services/humanResouceService";
import { setHumanResource } from "../store/humanResource";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const humanResource = useSelector((state) => state.humanResource.humanResource);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    let humanResourceService = new HumanResourceService();
    await humanResourceService.login(username, password).then((res) => {
      dispatch(setHumanResource(res.data));
      navigate("/human-resource/job-posts");
      toast.success("Giriş Başarılı");
    }).catch((err) => {
      dispatch(setHumanResource());
      toast.error(err.response.data.error_message);
    });
  };
  
  return (
    <div className="h-full">
      <LoginNavBar />
      <main className="wrapper flex justify-between items-center gap-20 ml-48 mr-48">
        <div className="flex flex-col items-center justify-start gap-10">
          <h1 className="text-6xl font-thin text-[#8f5849]">
            İş İlanı Oluşturmak için Giriş Yapın
          </h1>
          <form
            onSubmit={handleSubmit}
            className="flex flex-col gap-4 w-full text-xl "
          >
            <input
              type="text"
              placeholder="Kullanıcı Adı"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="border-2 border-gray-300 rounded-md px-4 py-2"
            />
            <input
              type="password"
              placeholder="Şifre"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="border-2 border-gray-300 rounded-md px-4 py-2"
            />
            <button
              type="submit"
              className="bg-obss-blue text-white rounded-md px-4 py-2"
            >
              Oturum Aç
            </button>
            {errorMessage && <p className="text-red-500">{errorMessage}</p>}
          </form>
        </div>

        <img
          src="https://media.licdn.com/media//AAYQAgSrAAgAAQAAAAAAAEFQ0SVTwDSXRqWvckCS4gYqEA.svg"
          alt="logo"
        />
      </main>
    </div>
  );
}