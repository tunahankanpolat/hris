import React, { useEffect, useState } from "react";
import ChecklistIcon from "@mui/icons-material/Checklist";
import BusinessIcon from "@mui/icons-material/Business";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import EditIcon from "@mui/icons-material/Edit";
import CheckIcon from "@mui/icons-material/Check";
import JobPostService from "../services/jobPostService";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import ToggleOnIcon from "@mui/icons-material/ToggleOn";
import ToggleOffIcon from "@mui/icons-material/ToggleOff";
import { getHumanResource } from "../store/storage";

export default function JobPostDetailForUpdate(props) {
  const humanResource = getHumanResource();
  const navigate = useNavigate();
  const [isTitleEditing, setTitleEditing] = useState(false);
  const [isLocationEditing, setLocationEditing] = useState(false);
  const [isDescriptionEditing, setDescriptionEditing] = useState(false);
  const [isCompanyEditing, setCompanyEditing] = useState(false);

  const [company, setCompany] = useState();
  const [title, setTitle] = useState();
  const [location, setLocation] = useState();
  const [description, setDescription] = useState();
  const [skills, setSkills] = useState();
  const [active, setActive] = useState();
  const [newSkill, setNewSkill] = useState("");
  const [dropdownVisible, setDropdownVisible] = useState(false);
  const [activationTime, setActivationTime] = useState();
  const [closureTime, setClosureTime] = useState();
  useEffect(() => {
    setTitle(props.title);
    setCompany(props.company);
    setLocation(props.location);
    setSkills(props.requiredSkills);
    setDescription(props.description);
    setActive(props.active);
    setActivationTime(props.activationTime);
    setClosureTime(props.closureTime);
  }, [props]);

  const handleSave = async (event) => {
    event.preventDefault();
    let jobPost = {
      jobPostId: props.jobPostId,
      title: title,
      company: company,
      location: location,
      description: description,
      requiredSkills: skills,
      closureTime: closureTime,
      activationTime: activationTime,
      active: active,
    };
    let jobPostService = new JobPostService();
    await jobPostService
      .updateJobPost(humanResource.token, jobPost)
      .then((result) => {
        props.onUpdate(jobPost);
        toast.success(result.data);
      })
      .catch((err) => {
        toast.error(err.response.data.error.toString());
      });
  };

  const handleDelete = async (event) => {
    event.preventDefault();
    let jobPost = { jobPostId: props.jobPostId };
    let jobPostService = new JobPostService();
    await jobPostService
      .deleteJobPost(humanResource.token, props.jobPostId)
      .then((result) => {
        props.onDelete(jobPost);
        toast.success(result.data);
      })
      .catch((err) => {
        toast.error(err.response.data.error.toString());
      });
  };

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

  const handleAddSkill = () => {
    if (newSkill.trim() === "") return;
    setSkills([...skills, newSkill.trim()]);
    setNewSkill("");
  };

  const handleDescriptionEdit = () => {
    if (isDescriptionEditing) {
      setDescription(document.getElementById("description-input").value);
    }
    setDescriptionEditing(!isDescriptionEditing);
  };
  const handleRemoveSkill = (skill) => {
    const updatedSkills = skills.filter((s) => s !== skill);
    setSkills(updatedSkills);
  };

  const toggleDropdown = () => {
    setDropdownVisible(!dropdownVisible);
  };
  return (
    <div className="w-full h-full flex flex-col px-7 gap-12">
      <div className="flex justify-between w-full gap-5">
        {active ? (
          <ToggleOnIcon
            className="cursor-pointer"
            onClick={() => setActive(false)}
            style={{ width: 40, height: 40, color: "#0a66c2" }}
          />
        ) : (
          <ToggleOffIcon
            className="cursor-pointer"
            onClick={() => setActive(true)}
            style={{ width: 40, height: 40, color: "gray" }}
          />
        )}
        <h2 className="text-xl font-bold text-obss-blue w-full cursor-pointer">
          {isTitleEditing ? (
            <input
              type="text"
              defaultValue={title}
              className="border rounded focus:outline-none w-[calc(100%-7px)]"
              id="title-input"
            />
          ) : (
            <div>
              <button
                id="dropdownDefaultButton"
                onClick={toggleDropdown}
                className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 h-10"
              >
                {title}
              </button>
              <div
                id="dropdown"
                className={`${
                  dropdownVisible ? "" : "hidden"
                } z-20 bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 absolute`}
              >
                <ul
                  className="py-2 text-sm text-gray-700 dark:text-gray-200 text-center"
                  aria-labelledby="dropdownDefaultButton"
                >
                  <li>
                    <button
                      onClick={() =>
                        navigate(
                          `/human-resource/job-posts/${props.jobPostId}/job-applications`
                        )
                      }
                      className="w-full block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                    >
                      Başvuraları Gör
                    </button>
                  </li>
                  <li>
                    <button
                      onClick={handleDelete}
                      className="w-full block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                    >
                      İlanı Sil
                    </button>
                  </li>
                </ul>
              </div>
            </div>
          )}
        </h2>

        {!isTitleEditing ? (
          <EditIcon
            className="text-obss-blue cursor-pointer"
            onClick={handleTitleEdit}
          />
        ) : (
          <CheckIcon
            className="text-obss-blue cursor-pointer border rounded bg-job-posts-background"
            onClick={handleTitleEdit}
          />
        )}
      </div>
      <div className="flex flex-col justify-start">
        <ul className="pl-5 space-y-3 text-gray-600 list-disc marker:text-[#0a66c2]">
          <li>Aktivasyon Zamanı : {activationTime}</li>
          <li>Kapanma Zamanı : {closureTime}</li>
        </ul>
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
              className="text-obss-blue cursor-pointer"
              onClick={handleCompanyEdit}
            />
          ) : (
            <CheckIcon
              className="text-obss-blue cursor-pointer border rounded bg-job-posts-background"
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
              className="text-obss-blue cursor-pointer"
              onClick={handleLocationEdit}
            />
          ) : (
            <CheckIcon
              className="text-obss-blue cursor-pointer border rounded bg-job-posts-background"
              onClick={handleLocationEdit}
            />
          )}
        </div>
      </div>

      <div className="flex justify-between">
        <div className="w-full">
          <div className="flex justify-between text-lg mb-2">
            <div>
              <ChecklistIcon className="mr-3" />
              Gereken Özellikler
            </div>

            <div className="flex items-center">
              <input
                type="text"
                id="requiredSkill"
                value={newSkill}
                onChange={(e) => setNewSkill(e.target.value)}
                className="border rounded py-2 px-3 text-gray-700"
              />
              <button
                type="button"
                onClick={handleAddSkill}
                className="bg-obss-blue text-white py-2 px-4 rounded ml-2"
              >
                Ekle
              </button>
            </div>
          </div>

          <div>
            {skills &&
              skills.map((skill, index) => (
                <div
                  key={index}
                  className="inline-block bg-gray-200 text-gray-800 rounded px-2 py-1 m-1"
                >
                  {skill}
                  <button
                    type="button"
                    onClick={() => handleRemoveSkill(skill)}
                    className="ml-2 text-red-600 font-bold"
                  >
                    X
                  </button>
                </div>
              ))}
          </div>
        </div>
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
            className="text-obss-blue cursor-pointer"
            onClick={handleDescriptionEdit}
          />
        ) : (
          <CheckIcon
            className="text-obss-blue cursor-pointer border rounded bg-job-posts-background"
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
