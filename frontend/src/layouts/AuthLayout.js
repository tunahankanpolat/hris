import { Outlet } from "react-router-dom";

export default function AuthLayout() {
  return (
    <div className="h-full">
      <Outlet />
    </div>
  );
}
