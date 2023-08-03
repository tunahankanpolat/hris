import axios from "axios";

export default class JobApplicationService {
  async updateStatus(token, id, status) {
    debugger;
    return await axios.put(
      process.env.REACT_APP_JOB_APPLICATIONS_URL +
        `/${id}/status?jobApplicationStatus=${status}`,
      {},
      {
        headers: {
          Authorization: "Bearer " + token,
        },
      }
    );
  }
}
