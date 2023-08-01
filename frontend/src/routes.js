import JobPosts from "./pages/JobPosts";
import Login from "./pages/Login";
import CandidateScrape from "./pages/CandidateScrape";
import CandidateAuthSuccess from "./pages/CandidateAuthSuccess.js";
import PrivateRoute from "./components/PrivateRoute";
import HumanResouceJobPosts from "./pages/HumanResourceJobPosts";
import JobPostApplications from "./pages/JobPostApplications";
const routes = [
    {
        path: '/',
        element: <Login/>
    },
    {
        path: 'login',
        element: <Login/>
    },
    {
        path: 'job-posts',
        element: <JobPosts/>
    },
    {
        path: 'candidate/scrape/skills',
        element: <CandidateScrape/>
    },
    {
        path: '/candidate/auth/success',
        element: <CandidateAuthSuccess/>
    },
    {
        path: '/human-resource/job-posts',
        element: <HumanResouceJobPosts/>
    },
    {
        path: '/human-resource/job-posts/:id/job-applications',
        element: <JobPostApplications/>
    },
]

const authCheck = routes => routes.map(route => {
    if(route?.auth) {
        route.element = <PrivateRoute>{route.element}</PrivateRoute>
    }

    if(route?.children) {
        route.children = authCheck(route.children)
    }
})

export default routes;