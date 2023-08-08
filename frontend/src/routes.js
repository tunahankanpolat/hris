import JobPosts from "./pages/JobPosts";
import Login from "./pages/Login";
import CandidateScrape from "./pages/CandidateScrape";
import CandidateAuthSuccess from "./pages/CandidateAuthSuccess.js";
import HumanResourcePrivateRoute from "./components/PrivateRouters/HumanResourcePrivateRoute";
import PublicPrivateRoute from "./components/PrivateRouters/PublicPrivateRoute";
import HumanResouceJobPosts from "./pages/HumanResourceJobPosts";
import JobPostApplications from "./pages/JobPostApplications";
import CandidateProfile from "./pages/CandidateProfile";
import CandidateJobApplication from "./pages/CandidateJobApplication";
import JobPostCreate from "./pages/JobPostCreate";
import Layout from "./layouts/Layout";
import CandidatePrivateRoute from "./components/PrivateRouters/CandidatePrivateRoute";
import CandidateSearch from "./pages/CandidateSearch";
import OAuth2RedirectHandler from "./components/OAuth2RedirectHandler ";
const routes = [
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        path: "/",
        element: <Login />,
        login: true,
      },
      {
        path: "/login",
        element: <Login />,
        login: true,
      },
      {
        path: "job-posts",
        element: <JobPosts />,
        login: false,
      },
      {
        path: "search/candidate/:keyword",
        element: <CandidateSearch />,
        humanResourceAuth: true,
      }
    ],
  },
  {
    path: "human-resource",
    element: <Layout />,
    humanResourceAuth: true,
    children: [
      {
        path: "job-posts",
        element: <HumanResouceJobPosts />,
      },
      {
        path: "job-posts/:id/job-applications",
        element: <JobPostApplications />,
      },
      {
        path: "job-posts/create",
        element: <JobPostCreate />,
      },
    ],
  },
  {
    path: "candidate",
    element: <Layout />,
    children: [
      {
        path: ":id",
        element: <CandidateProfile />,
        humanResourceAuth: true,
      },
      {
        path: "me",
        element: <CandidateProfile />,
        candidateAuth: true,
      },
      {
        path: ":id/job-applications",
        element: <CandidateJobApplication />,
        humanResourceAuth: true,
      },
      {
        path: "me/job-applications",
        element: <CandidateJobApplication />,
        candidateAuth: true,
      },
    ],
  },
  // {
  //     path: 'candidate/:id',
  //     element: <CandidateProfile/>
  // },
  // {
  //     path: 'candidate/:id/job-applications',
  //     element: <CandidateJobApplication/>
  // },
  {
    path: "candidate/scrape/skills",
    element: <CandidateScrape />,
  },
  {
    path: "oauth2/redirect",
    element: <OAuth2RedirectHandler/>
  }
  // {
  //     path: '/human-resource/job-posts',
  //     element: <HumanResouceJobPosts/>
  // },
  // {
  //     path: '/human-resource/job-posts/:id/job-applications',
  //     element: <JobPostApplications/>
  // },
];

const authCheck = (routes) =>
  routes.map((route) => {
    if (route?.humanResourceAuth) {
      route.element = (
        <HumanResourcePrivateRoute>{route.element}</HumanResourcePrivateRoute>
      );
    }
    if (route?.candidateAuth) {
      route.element = (
        <CandidatePrivateRoute>{route.element}</CandidatePrivateRoute>
      );
    }
    if (route?.login) {
      route.element = <PublicPrivateRoute>{route.element}</PublicPrivateRoute>;
    }
    if (route?.children) {
      route.children = authCheck(route.children);
    }

    return route;
  });

export default authCheck(routes);
