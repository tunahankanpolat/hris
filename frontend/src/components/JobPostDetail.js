import React from "react";
import ChecklistIcon from "@mui/icons-material/Checklist";
import BusinessIcon from "@mui/icons-material/Business";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import LaunchIcon from "@mui/icons-material/Launch";
import ClearIcon from "@mui/icons-material/Clear";

export default function JobPostDetail(props) {
  return (
    <div className="w-full h-full flex flex-col px-7 gap-12">
      <h2 className="text-xl font-bold">İş Başlığı</h2>
      <ul>
        <li>
          <BusinessIcon className="mr-3 mb-2" />
          Obss Teknoloji Anonim Şirketi
        </li>
        <li>
          <LocationOnIcon className="mr-3" />
          Ankara
        </li>
      </ul>

      <div>
        <h4 className="text-lg mb-2">
          <ChecklistIcon className="mr-3" />
          Gereken Özellikler
        </h4>
        <p>Spring, Backend, Fronend, AI</p>
      </div>

      {props.isApplied ?
        <div className="bg-zinc-600 rounded-full w-40 pt-2 pb-3 text-white flex text-center opacity-90 hover:opacity-100 transition-opacity">
          <ClearIcon className="mx-3 w-1/2 h-1/2" /> Başvuruyu Sil
        </div>
       : 
        <div className="bg-obss-blue rounded-full w-36 pt-2 pb-3 text-white flex text-center opacity-90 hover:opacity-100 transition-opacity">
          <LaunchIcon className="mx-3 w-1/2 h-1/2" /> Başvur
        </div>
      }
      <div>
        <h4 className="text-lg font-bold mb-2">İş Tanımı</h4>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptatibus,
          quibusdam. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam,
          voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam,
          voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam,
          voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam,
          voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam,
          voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam,
          voluptatum. Quisquam, voluptatum. Quisquam, voluptatum.
        </p>
      </div>
    </div>
  );
}
