import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import CandidateService from "../services/candidateService";

export default function CandidateProfile() {
  const { id } = useParams();
  const humanResource = useSelector(
    (state) => state.humanResource.humanResource
  );
  const [candidate, setCandidate] = useState(false);
  const candidateStore = useSelector((state) => state.candidate.candidate);

  useEffect(() => {
    let candidateService = new CandidateService();
    let token = humanResource.token;
    if(!id){
      token = candidateStore.token;
    }
    candidateService
      .getCandidateProfile(token, id)
      .then((result) => {
        setCandidate(result.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
  return (
    <main className="wrapper flex bg-job-posts-background pl-48 pr-48 pb-8">
      <div className="flex w-full h-full shadaow pt-8">
        <div className="bg-white rounded-3xl w-full h-full overflow-auto">
          <div className="w-full h-full">
            <div className="h-2/5 w-full">
              <img
                className="w-full rounded-3xl h-full object-center object-cover"
                src="cover.jpg"
              />
            </div>
            <div className="ml-10 flex flex-col items-center justify-start text-center -translate-y-10">
              <img
                className="flex items-center justify-center w-40 h-40 rounded-full"
                src={candidate && candidate.profilePicture}
              />
              <h1 className="text-2xl mt-3">
                {candidate && candidate.firstName + " " + candidate.lastName}
              </h1>
            </div>

            <div className="h-3/5 w-full rounded-3xl bg-white">
              <div className="flex flex-col items-start justify-end gap-5 ml-10 mr-10 mt-10 mb-10">
                <h2 className="text-2xl font-bold">İletişim Bilgileri</h2>
                <hr className="w-full border-gray-300" />
                <h3 className="text-xl">{candidate && candidate.email}</h3>
              </div>

              <div className="flex flex-col items-start justify-end gap-5 ml-10 mr-10 mt-20">
                <h2 className="text-2xl font-bold">Hakkında</h2>
                <hr className="w-full border-gray-300" />
                <h3 className="text-xl">{candidate && candidate.about}</h3>
              </div>

              <div className="flex flex-col items-start justify-end gap-5 ml-10 mr-10 mb-20 mt-20">
                <h2 className="text-2xl font-bold">Yetenekler</h2>
                <hr className="w-full border-gray-300" />
                <div>
                  {candidate &&
                    candidate.skills.map((skill, index) => (
                      <div
                        key={index}
                        className="inline-block bg-gray-200 text-gray-800 rounded px-2 py-1 m-1"
                      >
                        {skill}
                      </div>
                    ))}
                </div>
              </div>
              <hr className="w-full border-white" />
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}
