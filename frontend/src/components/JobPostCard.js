import React from "react";

export default function JobPostCard(props) {
  return (
    <div onClick={props.onClick} className="cursor-pointer w-full h-32 p-3 flex flex-col pl-12 pr-8 bg-white active:bg-blue-100 transition-colors">
      <hr className="border-gray-300 w-full mb-5" />
      <ul>
        <li className="text-xl font-bold text-obss-blue">{props.title}</li>
        <li className="text-s text-obss-gray">{props.company}</li>
        <li className="text-xs text-obss-gray">{props.location}</li>
        <li className="text-xs text-obss-gray">{props.activationTime}</li>
      </ul>
    </div>
  );
}
