import { useState } from "react";
import { useNavigate } from "react-router-dom";
import BanModal from "./BanModal";
export default function CandidateCard(props) {
  const navigate = useNavigate();

  const [showModal, setShowModal] = useState(false);

  const [profileDropdownVisible, setProfileDropdownVisible] = useState(false);

  const toggleProfileDropdown = () => {
    setProfileDropdownVisible(!profileDropdownVisible);
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
                className="text-white mb-2 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 h-10"
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
            <ul className="text-xs">
              <li className="text-obss-gray">
                Yetenekler:{" "}
                {props.skills?.map((skill, index) => (
                  <div
                    key={index}
                    className="inline-block bg-gray-200 text-gray-800 rounded px-2 py-1 m-1"
                  >
                    {skill}
                  </div>
                ))}
              </li>
              <li className=" text-obss-gray pt-4">
                Email adresi: {props.email}
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}
