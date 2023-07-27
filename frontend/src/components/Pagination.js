import React from "react";
import NavigateNextIcon from "@mui/icons-material/NavigateNext";
import NavigateBeforeIcon from "@mui/icons-material/NavigateBefore";

const Pagination = ({ currentPage, totalPages, onPageChange }) => {
  const pageNumbers = Array.from(
    { length: totalPages },
    (_, index) => index + 1
  );

  return (
    <nav className="flex items-center justify-center mt-4">
      <ul className="flex list-reset rounded border border-gray-300">
        <li
          className={`py-2 px-3 ${
            currentPage === 1
              ? "opacity-50 pointer-events-none"
              : "cursor-pointer"
          }`}
          onClick={() => onPageChange(currentPage - 1)}
        >
          <NavigateBeforeIcon />
        </li>
        {pageNumbers.map((pageNum) => (
          <li
            key={pageNum}
            className={`py-2 px-3 ${
              currentPage === pageNum
                ? "bg-blue-500 text-white"
                : "bg-white text-blue-500"
            } cursor-pointer`}
            onClick={() => onPageChange(pageNum)}
          >
            {pageNum}
          </li>
        ))}
        <li
          className={`py-2 px-3 ${
            currentPage === totalPages
              ? "opacity-50 pointer-events-none"
              : "cursor-pointer"
          }`}
          onClick={() => onPageChange(currentPage + 1)}
        >
          <NavigateNextIcon />
        </li>
      </ul>
    </nav>
  );
};

export default Pagination;
