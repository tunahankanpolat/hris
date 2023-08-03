import React from "react";
import SyncIcon from "@mui/icons-material/Sync";
import ExpandCircleDownIcon from "@mui/icons-material/ExpandCircleDown";
import BlockIcon from "@mui/icons-material/Block";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import BanModal from "./BanModal";
export default function JobPostApplicationCard(props) {
  const [dropdownVisible, setDropdownVisible] = useState(false);
  const navigate = useNavigate();
  const toggleDropdown = () => {
    setDropdownVisible(!dropdownVisible);
  };

  const [showModal, setShowModal] = useState(false);

  const [profileDropdownVisible, setProfileDropdownVisible] = useState(false);

  const toggleProfileDropdown = () => {
    setProfileDropdownVisible(!profileDropdownVisible);
  };

  const handleUpdateStatus = (status) => {
    toggleDropdown();
    props.onUpdateStatus(props.jobApplicationId, status);
  };
  return (
    <div className="w-full h-32 p-3 flex flex-col pl-12 pr-8 bg-white">
      <hr className="border-gray-300 w-full mb-5" />
      <div className="flex justify-between w-full h-full">
        <div className="flex justify-between h-full">
          <img
            className="flex items-center justify-center h-full rounded-full mr-5 object-center object-cover"
            src={
              props.profilePicture
                ? props.profilePicture
                : process.env.REACT_APP_DEFAULT_PROFILE_AVATAR_URL
            }
            alt="User Avatar"
          />
          <div className="flex flex-col">
            <div className="relative">
              <button
                id="profileDropDown"
                className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 h-10"
                onClick={toggleProfileDropdown}
              >
                {props.firstName} {" " + props.lastName}
              </button>
              <div
                id="profileDropdown"
                className={`${
                  profileDropdownVisible ? "" : "hidden"
                } z-20 bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 absolute`}
              >
                <ul
                  className="py-2 text-sm text-gray-700 dark:text-gray-200 text-center"
                  aria-labelledby="profileDropDown"
                >
                  <li>
                    <button
                      onClick={() =>
                        navigate(`/candidate/${props.candidateId}`)
                      }
                      className="w-full block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                    >
                      Profili Görüntüle
                    </button>
                  </li>
                  <li>
                    <button
                      onClick={() =>
                        navigate(
                          `/candidate/${props.candidateId}/job-applications`
                        )
                      }
                      className="w-full block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                    >
                      Başvuruları Gör
                    </button>
                  </li>
                  <li>
                    <button
                      onClick={() => setShowModal(true)}
                      className="w-full block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                    >
                      Kullanıcı Banla
                    </button>
                  </li>
                </ul>

                {showModal && (
                  <BanModal
                    candidateId={props.candidateId}
                    onClose={() => setShowModal(false)}
                  />
                )}
              </div>
            </div>

            <ul>
              <li className="text-s text-obss-gray">
                {(() => {
                  if (props.status === "PROCESSING")
                    return (
                      <div className=" flex justify-between items-center item gap-3">
                        <SyncIcon className="text-orange-200 cursor-pointer" />

                        <div className="text-xs text-obss-gray  ">
                          Başvuru İşleme Alındı
                        </div>
                      </div>
                    );
                  else if (props.status === "ACCEPTED")
                    return (
                      <div className=" flex justify-between items-center item gap-3">
                        <ExpandCircleDownIcon className="text-obss-blue cursor-pointer" />

                        <div className="text-xs text-obss-gray  ">
                          Başvuru Kabul Edildi
                        </div>
                      </div>
                    );
                  else if (props.status === "REJECTED")
                    return (
                      <div className=" flex justify-between items-center item gap-3">
                        <BlockIcon className="text-red-500 cursor-pointer" />

                        <div className="text-xs text-obss-gray ">
                          Başvuru Red Edildi
                        </div>
                      </div>
                    );
                })()}
              </li>
              <li className="text-xs text-obss-gray pt-4">
                Başvurma Tarihi: {props.applicationTime}
              </li>
            </ul>
          </div>
        </div>

        <div>
          <button
            id="dropdownDefaultButton"
            onClick={toggleDropdown}
            className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 h-10"
            type="button"
          >
            Başvuruyu Güncelle
            <svg
              className="w-2.5 h-2.5 ml-2.5"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 10 6"
            >
              <path
                stroke="currentColor"
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="m1 1 4 4 4-4"
              />
            </svg>
          </button>
          <div
            id="dropdown"
            className={`${
              dropdownVisible ? "" : "hidden"
            } z-20 bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 relative`}
          >
            <ul
              className="text-center py-2 text-sm text-gray-700 dark:text-gray-200"
              aria-labelledby="dropdownDefaultButton"
            >
              <li>
                <button
                  onClick={() => handleUpdateStatus("PROCESSING")}
                  className="w-full block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                >
                  Başvuruyu İşleme Al
                </button>
              </li>
              <li>
                <button
                  onClick={() => handleUpdateStatus("ACCEPTED")}
                  className="w-full block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                >
                  Başvuruyu Kabul Et
                </button>
              </li>
              <li>
                <button
                  onClick={() => handleUpdateStatus("REJECTED")}
                  className="w-full block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                >
                  Başvuruyu Red Et
                </button>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}
