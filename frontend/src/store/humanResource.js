import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    humanResource: JSON.parse(localStorage.getItem("humanResource")) || false
}

const humanResource = createSlice({
    name: "humanResource",
    initialState,
    reducers: {
        setHumanResource(state, action)  {
            if(action.payload){
                localStorage.setItem("humanResource", JSON.stringify(action.payload))
            }else{
                localStorage.removeItem("humanResource")
            }
            state.humanResource = action.payload
        }
    }
})
    

export const { setHumanResource } = humanResource.actions
export default humanResource.reducer