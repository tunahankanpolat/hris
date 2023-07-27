import React from "react";
import NavBar from "../layouts/NavBar";

export default function CandidateProfile() {
  return (
    <div className="h-full">
      <NavBar />
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
              <div class="ml-10 flex flex-col items-center justify-start text-center -translate-y-10">
                <img
                  className="flex items-center justify-center w-40 h-40 rounded-full"
                  src="https://media.licdn.com/dms/image/D4D03AQGdU0AzoZjalA/profile-displayphoto-shrink_800_800/0/1678642073353?e=1695859200&v=beta&t=RXHdCRMGalkkip8l5tLN2i-zXLEPiYfCzjU-woMZDk8"
                />
                <h1 className="text-2xl mt-3">Tunahan Kanpolat</h1>
              </div>

              <div className="h-3/5 w-full rounded-3xl bg-white">
                <div className="flex flex-col items-start justify-end gap-5 ml-10 mr-10 mt-10 mb-10">
                  <h2 className="text-2xl font-bold">İletişim Bilgileri</h2>
                  <hr className="w-full border-gray-300" />
                  <h3 className="text-xl">kanpolatunahan@gmail.com</h3>
                </div>

                <div className="flex flex-col items-start justify-end gap-5 ml-10 mr-10 mt-20">
                  <h2 className="text-2xl font-bold">Hakkında</h2>
                  <hr className="w-full border-gray-300" />
                  <h3 className="text-xl">
                    SADSAD ASD ASD ASD ASDAS DAS DASDSSAD SAD
                  </h3>
                </div>

                <div className="flex flex-col items-start justify-end gap-5 ml-10 mr-10 mb-20 mt-20">
                  <h2 className="text-2xl font-bold">Yetenekler</h2>
                  <hr className="w-full border-gray-300" />
                  <h3 className="text-xl">Backend, Java, Spring</h3>
                </div>
                <hr className="w-full border-white" />
              </div>
            </div>
          </div>
        </div>
      </main>
      
    </div>
  );
}
