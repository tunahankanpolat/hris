import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import Login from "./pages/Login";
import Home from "./pages/Home";
import CandidateJobApplication from "./pages/CandidateJobApplication";
import HumanResourceJobPosts from "./pages/HumanResourceJobPosts";
import CandidateProfile from "./pages/CandidateProfile";
import JobPostApplications from "./pages/JobPostApplications";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <BrowserRouter>
    <JobPostApplications />
  </BrowserRouter>
);
