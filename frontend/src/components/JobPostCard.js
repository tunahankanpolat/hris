import React from "react";

export default function JobPostCard() {
  return (
    //tıklandığında background color
    <div className="cursor-pointer w-full h-32 p-3 flex flex-col pl-12 pr-8 bg-white active:bg-blue-100 transition-colors">
      <hr className="border-gray-300 w-full mb-5" />
      <ul>
        <li className="text-xl font-bold text-obss-blue">İş Başlığı</li>
        <li className="text-s text-obss-gray">Şirket Adı</li>
        <li className="text-xs text-obss-gray">Şehir</li>
        <li className="text-xs text-obss-gray">Oluşturulma Tarihi</li>
      </ul>
    </div>
  );
}
