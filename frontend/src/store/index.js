import { configureStore } from "@reduxjs/toolkit";
import candidate from "./candidate";
import humanResource from "./humanResource";
const store = configureStore({
    reducer: {
        candidate,
        humanResource,
    }
})

export default store;