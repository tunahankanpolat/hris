import axios from "axios";

export default class CandidateService {
  async logout(token) {
    return await axios.get(
      process.env.REACT_APP_CANDIDATE_BASE_URL + "/logout",
      {
        headers: {
          Authorization: "Bearer " + token,
        },
      }
    );
  }

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
  scrapeLinkedinProfile(token, linkedinUrl) {
    return axios.get(
      process.env.REACT_APP_CANDIDATE_SCRAPE_SKILLS_URL +
        `?linkedinUrl=${linkedinUrl}`,
      {
        headers: {
          Authorization: "Bearer " + token,
        },
      }
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
}
