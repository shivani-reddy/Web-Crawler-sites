import MainPage from "./components/MainPage";

import "./App.css";
import { useState, useEffect, useCallback } from "react";

//This is the main components and the starting point of application where the state of reviews, handles data fetching , filtering and infinate scrolling is done.
const App = () => {
  //state to hold all reviews fetched from the Json file
  
  //state to hold filterted reviews based on serach query
  const [filteredReviews, setFilteredReviews] = useState([]);
  //state to hold reviews currently displayed on the page
  const [displayedReviews, setDisplayedReviews] = useState([]);
  //state to control the number of reviews loaded at a time
  const [loadCount, setLoadCount] = useState(10);

  

  

  //to load more reviews when the use scrollers to the bottom
  const LoadMoreReviews = useCallback(() => {
    setLoadCount(loadCount + 10);
    setDisplayedReviews(filteredReviews.slice(0, loadCount + 10));
  }, [loadCount, filteredReviews]);

  //Uses the useEffect hook to add an event listener for the scroll event when the component mounts and removes it when the component unmounts or when filteredReviews or loadCount change.
  useEffect(() => {
    const handleScroll = () => {
      if (
        window.innerHeight + document.documentElement.scrollTop ===
        document.documentElement.offsetheight
      ) {
        LoadMoreReviews();
      }
    };
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, [LoadMoreReviews]);

  return (
    <MainPage />
    
  );
};

export default App;
