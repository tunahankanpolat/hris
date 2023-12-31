import React from "react";
import SyncIcon from "@mui/icons-material/Sync";
import ExpandCircleDownIcon from "@mui/icons-material/ExpandCircleDown";
import BlockIcon from "@mui/icons-material/Block";

export default function CandidateJobApplicationCard(props) {
  return (
    <div onClick={props.onClick} className="cursor-pointer w-full h-32 p-3 flex flex-col pl-12 pr-8 bg-white active:bg-blue-100 transition-colors">
      <hr className="border-gray-300 w-full mb-5" />
      <div className="flex justify-between">
        <div className="flex flex-col">
          <ul>
            <li className="text-xl font-bold text-obss-blue">{props.title}</li>
            <li className="text-s text-obss-gray">{props.company}</li>
            <li className="text-xs text-obss-gray">{props.location}</li>
            <li className="text-xs text-obss-gray">{props.applicationTime}</li>
          </ul>
        </div>
        <div className="flex flex-col justify-center items-center">
          {(() => {
            if (props.status === "PROCESSING")
              return (
                <div className=" flex justify-between items-center item gap-3">
                  <SyncIcon className="text-orange-200" />

                  <div className="text-xs text-obss-gray whitespace-break-spaces ">
                    Başvuru{"\n"}İşleme{"\n"}Alındı
                  </div>
                </div>
              );
            else if (props.status === "ACCEPTED")
              return (
                <div className=" flex justify-between items-center item gap-3">
                  <ExpandCircleDownIcon className="text-obss-blue" />

                  <div className="text-xs text-obss-gray whitespace-break-spaces ">
                    Başvuru{"\n"}Kabul{"\n"}Edildi
                  </div>
                </div>
              );
            else if (props.status === "REJECTED")
              return (
                <div className=" flex justify-between items-center item gap-3">
                  <BlockIcon className="text-red-500" />

                  <div className="text-xs text-obss-gray whitespace-break-spaces">
                    Başvuru{"\n"}Red{"\n"}Edildi
                  </div>
                </div>
              );
          })()}
        </div>
      </div>
    </div>
  );
}
