import React from "react";
import SyncIcon from "@mui/icons-material/Sync";
import ExpandCircleDownIcon from "@mui/icons-material/ExpandCircleDown";
import BlockIcon from "@mui/icons-material/Block";
import { useState } from "react";
export default function JobPostApplicationCard(props) {
  const [dropdownVisible, setDropdownVisible] = useState(false);

  const toggleDropdown = () => {
    setDropdownVisible(!dropdownVisible);
  };

  const [profileDropdownVisible, setProfileDropdownVisible] = useState(false);

  const toggleProfileDropdown = () => {
    setProfileDropdownVisible(!profileDropdownVisible);
  };
  return (
    //tıklandığında background color
    <div className="w-full h-32 p-3 flex flex-col pl-12 pr-8 bg-white">
      <hr className="border-gray-300 w-full mb-5" />
      <div className="flex justify-between w-full h-full">
        <div className="flex justify-between h-full">
          <img
            className="flex items-center justify-center h-full rounded-full mr-5"
            src="https://media.licdn.com/dms/image/D4D03AQGdU0AzoZjalA/profile-displayphoto-shrink_800_800/0/1678642073353?e=1695859200&v=beta&t=RXHdCRMGalkkip8l5tLN2i-zXLEPiYfCzjU-woMZDk8"
          />
          <div className="flex flex-col">
            <div className="relative">
              <button
                id="profileDropDown"
                className="text-xl font-bold text-obss-blue"
                onClick={toggleProfileDropdown}
              >
                Başvuranın Adı ve Soyadı
              </button>
              <div
                id="profileDropdown"
                className={`${
                  profileDropdownVisible ? "" : "hidden"
                } z-20 bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 absolute`}
              >
                <ul
                  className="py-2 text-sm text-gray-700 dark:text-gray-200"
                  aria-labelledby="profileDropDown"
                >
                  <li>
                    <a
                      href="#"
                      className="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                    >
                      Profili Görüntüle
                    </a>
                  </li>
                  <li>
                    <a
                      href="#"
                      className="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                    >
                      Başvuruları Gör
                    </a>
                  </li>
                  <li>
                    <a
                      href="#"
                      className="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                    >
                      Kullanıcı Banla
                    </a>
                  </li>
                </ul>
              </div>
            </div>

            <ul>
              <li className="text-s text-obss-gray">
                {(() => {
                  // if (props.state === "WAITING")
                  //   return (
                  //     <div className=" flex justify-between items-center item gap-3">
                  //       <HourglassTopIcon className="text-obss-gray cursor-pointer" />
                  //       <div className="text-xs text-obss-gray  ">
                  //         Başvuru İletildi
                  //       </div>
                  //     </div>
                  //   );
                  if (props.state === "PROCESSING")
                    return (
                      <div className=" flex justify-between items-center item gap-3">
                        <SyncIcon className="text-orange-200 cursor-pointer" />

                        <div className="text-xs text-obss-gray  ">
                          Başvuru İşleme Alındı
                        </div>
                      </div>
                    );
                  else if (props.state === "ACCEPTED")
                    return (
                      <div className=" flex justify-between items-center item gap-3">
                        <ExpandCircleDownIcon className="text-obss-blue cursor-pointer" />

                        <div className="text-xs text-obss-gray  ">
                          Başvuru Kabul Edildi
                        </div>
                      </div>
                    );
                  else if (props.state === "REJECTED")
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
              <li className="text-xs text-obss-gray pt-4">Başvurma Tarihi</li>
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
              className="py-2 text-sm text-gray-700 dark:text-gray-200"
              aria-labelledby="dropdownDefaultButton"
            >
              <li>
                <a
                  href="#"
                  className="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                >
                  Başvuruyu İşleme Al
                </a>
              </li>
              <li>
                <a
                  href="#"
                  className="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                >
                  Başvuruyu Kabul Et
                </a>
              </li>
              <li>
                <a
                  href="#"
                  className="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white"
                >
                  Başvuruyu Red Et
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}
