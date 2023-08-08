import { useState } from "react";
import JobPostService from "../services/jobPostService";
import { toast } from "react-toastify";
import { getHumanResource } from "../store/storage";

export default function JobPostCreate() {
  const [company, setCompany] = useState("");
  const [location, setLocation] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [activationTime, setActivationTime] = useState("");
  const [closureTime, setClosureTime] = useState("");
  const [requiredSkills, setRequiredSkills] = useState([]);
  const [newSkill, setNewSkill] = useState("");
  const humanResource = getHumanResource();
  // Form submit işlemi
  const handleSubmit = (e) => {
    e.preventDefault();
    const jobPost = {
      company,
      location,
      title,
      description,
      activationTime,
      closureTime,
      requiredSkills,
    };

    let jobPostService = new JobPostService();
    jobPostService
      .createJobPost(humanResource.token, jobPost)
      .then((result) => {
        debugger;
        toast.success(result.data);
      })
      .catch((err) => {
        debugger;
        toast.error(err.response.data.error_message.toString());
      });
  };
  // Yeni beceri ekleme işlemi
  const handleAddSkill = () => {
    if (newSkill.trim() === "") return;
    setRequiredSkills([...requiredSkills, newSkill.trim()]);
    setNewSkill("");
  };

  // Beçeri silme işlemi
  const handleRemoveSkill = (skill) => {
    const updatedSkills = requiredSkills.filter((s) => s !== skill);
    setRequiredSkills(updatedSkills);
  };
  return (
    <main className="wrapper flex bg-job-posts-background pl-48 pr-48 pb-8">
      <div className="w-full h-full shadaow pt-8">
        <div className="bg-white rounded-3xl w-full h-full overflow-auto p-8">
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label
                htmlFor="company"
                className="block text-gray-700 font-bold"
              >
                Şirket Adı
              </label>
              <input
                type="text"
                id="company"
                value={company}
                onChange={(e) => setCompany(e.target.value)}
                className="border rounded w-full py-2 px-3 text-gray-700"
                required
              />
            </div>
            <div className="mb-4">
              <label
                htmlFor="location"
                className="block text-gray-700 font-bold"
              >
                Konum
              </label>
              <input
                type="text"
                id="location"
                value={location}
                onChange={(e) => setLocation(e.target.value)}
                className="border rounded w-full py-2 px-3 text-gray-700"
                required
              />
            </div>
            <div className="mb-4">
              <label htmlFor="title" className="block text-gray-700 font-bold">
                Başlık
              </label>
              <input
                type="text"
                id="title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                className="border rounded w-full py-2 px-3 text-gray-700"
                required
              />
            </div>
            <div className="mb-4">
              <label
                htmlFor="requiredSkill"
                className="block text-gray-700 font-bold"
              >
                Gerekli Yetenekler
              </label>
              <div className="flex items-center">
                <input
                  type="text"
                  id="requiredSkill"
                  value={newSkill}
                  onChange={(e) => setNewSkill(e.target.value)}
                  className="border rounded w-full py-2 px-3 text-gray-700"
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
              {requiredSkills.map((skill, index) => (
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
            <div className="mb-4">
              <label
                htmlFor="description"
                className="block text-gray-700 font-bold"
              >
                Açıklama
              </label>
              <textarea
                id="description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                className="border rounded w-full py-2 px-3 text-gray-700"
                required
              />
            </div>
            <div className="mb-4">
              <label
                htmlFor="activationTime"
                className="block text-gray-700 font-bold"
              >
                Aktif Olma Tarihi
              </label>
              <input
                type="date"
                id="activationTime"
                value={activationTime}
                onChange={(e) => setActivationTime(e.target.value)}
                className="border rounded w-full py-2 px-3 text-gray-700"
                required
              />
            </div>
            <div className="mb-4">
              <label
                htmlFor="closureTime"
                className="block text-gray-700 font-bold"
              >
                Kapanma Tarihi
              </label>
              <input
                type="datetime-local"
                id="closureTime"
                value={closureTime}
                onChange={(e) => setClosureTime(e.target.value)}
                className="border rounded w-full py-2 px-3 text-gray-700"
                required
              />
            </div>
            <div className="text-center">
              <button
                type="submit"
                className="bg-blue-500 text-white py-2 px-4 rounded"
              >
                Submit
              </button>
            </div>
          </form>
        </div>
      </div>
    </main>
  );
}
