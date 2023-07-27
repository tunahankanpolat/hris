import React, { useState } from "react";
import ChecklistIcon from "@mui/icons-material/Checklist";
import BusinessIcon from "@mui/icons-material/Business";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import EditIcon from "@mui/icons-material/Edit";
import CheckIcon from "@mui/icons-material/Check";

export default function JobPostDetailForUpdate(props) {
  const [isTitleEditing, setTitleEditing] = useState(false);
  const [title, setTitle] = useState("İş Başlığı");

  const [isCompanyEditing, setCompanyEditing] = useState(false);
  const [company, setCompany] = useState("Obss Teknoloji Anonim Şirketi");

  const [isLocationEditing, setLocationEditing] = useState(false);
  const [location, setLocation] = useState("Ankara");

  const [isSkillsEditing, setSkillsEditing] = useState(false);
  const [skills, setSkills] = useState("Spring, Backend, Frontend, AI");

  const [isDescriptionEditing, setDescriptionEditing] = useState(false);
  const [description, setDescription] = useState(
    "Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptatibus, quibusdam. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum. Quisquam, voluptatum."
  );

  const handleTitleEdit = () => {
    if (isTitleEditing) {
      setTitle(document.getElementById("title-input").value);
    }
    setTitleEditing(!isTitleEditing);
  };

  const handleCompanyEdit = () => {
    if (isCompanyEditing) {
      setCompany(document.getElementById("company-input").value);
    }
    setCompanyEditing(!isCompanyEditing);
  };

  const handleLocationEdit = () => {
    if (isLocationEditing) {
      setLocation(document.getElementById("location-input").value);
    }
    setLocationEditing(!isLocationEditing);
  };

  const handleSkillsEdit = () => {
    if (isSkillsEditing) {
      setSkills(document.getElementById("skills-input").value);
    }
    setSkillsEditing(!isSkillsEditing);
  };

  const handleDescriptionEdit = () => {
    if (isDescriptionEditing) {
      setDescription(document.getElementById("description-input").value);
    }
    setDescriptionEditing(!isDescriptionEditing);
  };

  const handleSave = () => {
    // Perform any save actions here (e.g., API calls, etc.)
    // For simplicity, we'll just log the values for now
    console.log("Title:", title);
    console.log("Company:", company);
    console.log("Location:", location);
    console.log("Skills:", skills);
    console.log("Description:", description);
  };

  return (
    <div className="w-full h-full flex flex-col px-7 gap-12">
      <div className="flex justify-between w-full">
        <h2 className="text-xl font-bold w-full">
          {isTitleEditing ? (
            <input
              type="text"
              defaultValue={title}
              className="border rounded focus:outline-none w-[calc(100%-7px)]"
              id="title-input"
            />
          ) : (
            title
          )}
        </h2>
        {!isTitleEditing ? (
          <EditIcon
            className="text-obss-gray cursor-pointer"
            onClick={handleTitleEdit}
          />
        ) : (
          <CheckIcon
            className="text-obss-gray cursor-pointer"
            onClick={handleTitleEdit}
          />
        )}
      </div>
      <div>
        <div className="flex justify-between">
          <div className="w-full">
            <BusinessIcon className="mr-3 mb-2" />
            {isCompanyEditing ? (
              <input
                type="text"
                defaultValue={company}
                className="border rounded focus:outline-none w-[calc(100%-40px)]"
                id="company-input"
              />
            ) : (
              company
            )}
          </div>
          {!isCompanyEditing ? (
            <EditIcon
              className="text-obss-gray cursor-pointer"
              onClick={handleCompanyEdit}
            />
          ) : (
            <CheckIcon
              className="text-obss-gray cursor-pointer"
              onClick={handleCompanyEdit}
            />
          )}
        </div>
        <div className="flex justify-between">
          <div className="w-full">
            <LocationOnIcon className="mr-3" />
            {isLocationEditing ? (
              <input
                type="text"
                defaultValue={location}
                className="border rounded focus:outline-none w-[calc(100%-40px)]"
                id="location-input"
              />
            ) : (
              location
            )}
          </div>
          {!isLocationEditing ? (
            <EditIcon
              className="text-obss-gray cursor-pointer"
              onClick={handleLocationEdit}
            />
          ) : (
            <CheckIcon
              className="text-obss-gray cursor-pointer"
              onClick={handleLocationEdit}
            />
          )}
        </div>
      </div>

      <div className="flex justify-between">
        <div className="w-full">
          <h4 className="text-lg mb-2">
            <ChecklistIcon className="mr-3" />
            Gereken Özellikler
          </h4>
          {isSkillsEditing ? (
            <input
              type="text"
              defaultValue={skills}
              className="border rounded focus:outline-none w-full"
              id="skills-input"
            />
          ) : (
            <p>{skills}</p>
          )}
        </div>
        {!isSkillsEditing ? (
          <EditIcon
            className="text-obss-gray cursor-pointer"
            onClick={handleSkillsEdit}
          />
        ) : (
          <CheckIcon
            className="text-obss-gray cursor-pointer"
            onClick={handleSkillsEdit}
          />
        )}
      </div>

      <div className="flex justify-between">
        <div className="w-full">
          <h4 className="text-lg font-bold mb-2">İş Tanımı</h4>
          {isDescriptionEditing ? (
            <textarea
              defaultValue={description}
              className="border rounded focus:outline-none resize-none w-full h-40"
              id="description-input"
            />
          ) : (
            <p>{description}</p>
          )}
        </div>
        {!isDescriptionEditing ? (
          <EditIcon
            className="text-obss-gray cursor-pointer"
            onClick={handleDescriptionEdit}
          />
        ) : (
          <CheckIcon
            className="text-obss-gray cursor-pointer"
            onClick={handleDescriptionEdit}
          />
        )}
      </div>

      <button
        className="bg-blue-500 hover:bg-obss-blue text-white font-bold py-2 px-4 rounded focus:outline-none mb-4"
        onClick={handleSave}
      >
        Save
      </button>
      <hr className="border-white w-full" />
    </div>
  );
}
