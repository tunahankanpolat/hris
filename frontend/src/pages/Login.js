import React from "react";
import NavBar from "../layouts/NavBar";

export default function Login() {
  return (
    <div className="h-full">
      <NavBar />
      <main className="wrapper flex justify-between items-center gap-20 ml-48 mr-48">
        <div className="flex flex-col items-center justify-start gap-10">
          <h1 className="text-6xl font-thin text-[#8f5849]">
            İş İlanı Oluşturmak için Giriş Yapın
          </h1>
          <form className="flex flex-col gap-4 w-full text-xl ">
            <input
              type="text"
              placeholder="Kullanıcı Adı"
              className="border-2 border-gray-300 rounded-md px-4 py-2"
            />
            <input
              type="password"
              placeholder="Şifre"
              className="border-2 border-gray-300 rounded-md px-4 py-2"
            />
            <button className="bg-obss-blue text-white rounded-md px-4 py-2">
              Oturum Aç
            </button>
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
