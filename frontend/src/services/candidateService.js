import axios from "axios";

export default class CandidateService {
  async getCandidateProfile(token, candidateId) {
    if (!candidateId) {
      return await axios.get(process.env.REACT_APP_CANDIDATE_BASE_URL + `/me`, {
        headers: {
          Authorization: "Bearer " + token,
        },
      });
    } else {
      return await axios.get(
        process.env.REACT_APP_CANDIDATE_BASE_URL + `/${candidateId}`,
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      );
    }
  }
  async scrapeSkills(linkedinUrl) {
    return await axios.get(
      process.env.REACT_APP_CANDIDATE_SCRAPE_SKILLS_URL +
        `?linkedinUrl=${linkedinUrl}`
    );
  }
  async getJobApplications(token, page, size, candidateId) {
    if (!candidateId) {
      return await axios.get(
        process.env.REACT_APP_CANDIDATE_JOB_APPLICATIONS_URL +
          `/${page}/${size}`,
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      );
    } else {
      return await axios.get(
        process.env.REACT_APP_CANDIDATE_BASE_URL +
          `/${candidateId}/jobApplications/${page}/${size}`,
        {
          headers: {
            Authorization: "Bearer " + token,
          },
        }
      );
    }
  }

  async login() {
    return await axios.get(process.env.REACT_APP_CANDIDATE_LOGIN_URL);
  }
}
