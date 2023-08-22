import React from "react";
import ChecklistIcon from "@mui/icons-material/Checklist";
import BusinessIcon from "@mui/icons-material/Business";
import LocationOnIcon from "@mui/icons-material/LocationOn";

export default function CandidateJobApplicationDetail(props) {
  return (
    <div className="w-full h-full flex flex-col px-7 gap-12">
      <div>
        <h2 className="text-obss-blue text-2xl font-bold">{props.title}</h2>
        <h4 className="mt-2">{props.code}</h4>
      </div>
      <ul>
        <li>
          <BusinessIcon className="mr-3 mb-2" />
          {props.company}
        </li>
        <li>
          <LocationOnIcon className="mr-3" />
          {props.location}
        </li>
      </ul>

      <div>
        <h4 className="text-lg mb-2">
          <ChecklistIcon className="mr-3" />
          Gereken Özellikler
        </h4>
        <div>
          {props.requiredSkills.map((skill, index) => (
            <div
              key={index}
              className="inline-block bg-gray-200 text-gray-800 rounded px-2 py-1 m-1"
            >
              {skill}
            </div>
          ))}
        </div>
      </div>

      <div>
        <h4 className="text-lg font-bold mb-2">İş Tanımı</h4>
        <p>{props.description}</p>
      </div>
    </div>
  );
}
