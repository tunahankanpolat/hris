import axios from "axios";
export default class HumanResourceService {
  login(userName, password) {
    return axios.post(process.env.REACT_APP_HUMAN_RESOURCE_LOGIN_URL, {
      userName: userName,
      password: password,
    });
  }
  getJobPosts(token, page, size) {
    return axios.get(
      process.env.REACT_APP_HUMAN_RESOURCE_JOB_POSTS_URL + `/${page}/${size}`,
      {
        headers: {
          Authorization: "Bearer " + token,
        },
      }
    );
  }
}
