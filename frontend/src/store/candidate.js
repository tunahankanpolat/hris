import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    candidate: JSON.parse(localStorage.getItem("candidate")) || false
}

const candidate = createSlice({
    name: "candidate",
    initialState,
    reducers: {
        setCandidate(state, action)  {
            if(action.payload){
                localStorage.setItem("candidate", JSON.stringify(action.payload))
            }else{
                localStorage.removeItem("candidate")
            }
            state.candidate = action.payload
        }
    }
})
    

export const { setCandidate } = candidate.actions
export default candidate.reducer