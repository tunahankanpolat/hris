let setCandidate = (payload) => {
    if(payload){
        localStorage.setItem("candidate", JSON.stringify(payload))
    }else{
        localStorage.removeItem("candidate")
    }
}

let setHumanResource = (payload) => {
    if(payload){
        localStorage.setItem("humanResource", JSON.stringify(payload))
    }else{
        localStorage.removeItem("humanResource")
    }
}

let getCandidate = () => {
    return JSON.parse(localStorage.getItem("candidate")) || false
}

let getHumanResource = () => {
    return JSON.parse(localStorage.getItem("humanResource")) || false
}

export { setCandidate, setHumanResource, getCandidate, getHumanResource }