import axios from "axios";
export default class BlacklistService {
    async addBlacklist(token, banRequest) {
        return await axios.post(process.env.REACT_APP_BLACKLIST_URL, banRequest, {
            headers: {
                Authorization: "Bearer " + token,
            },
        });
    }
}
