import React, { useState, useEffect, useRef } from "react";

const SearchBox = ({ searchedTopic }) => {
  const [query, setQuery] = useState("");
  const [suggestions, setSuggestions] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [topic, setTopic] = useState();
  const [showSuggestions, setShowSuggestions] = useState(false);
  const [selectedTopic, setSelectedTopic] = useState();
  const suggestionsRef = useRef();

    useEffect(() => {
      console.log("Selected topic:", selectedTopic); // Line should show the selected Topic
      fetch("cochrane_reviews.json")
        .then((response) => response.json())
        .then((data) => {
          const topics = [];
          data.forEach((element) => {
            topics.push(element[0].topic.toLowerCase());
          });
            setTopic(topics);
            console.log("Fetched topics:", topics); //fetched topics
        })
        .catch((error) => console.error("Error fetching topics:", error));
    }, []);

    useEffect(() => {
      //Write a if class check if the event is empty then get back to the same function
      if (query === "") {
        setSuggestions([]);
        setShowSuggestions(false);
        searchedTopic(""); //Empty search box reloads back and gets all the reviews
        return;
      }
      if (query.length > 3) {
        setIsLoading(true);
        try {
          const filteredResults = topic.filter((element) =>
            element.includes(query)
          );
          setSuggestions(filteredResults);
          setIsLoading(false);
          setShowSuggestions(true);
        } catch (error) {
          console.error("Error fetching suggestions:", error);
          setIsLoading(false);
        }
      } else {
        setSuggestions([]);
        setShowSuggestions(false);
      }
    }, [query,topic]);

    const handleInputChange = (event) => {;
        setQuery(event.target.value.toLowerCase());
    }


    const handleSuggestionClick = (suggestion) => {
      console.log("Suggestion clicked:", suggestion); //suggestion print
      searchedTopic(suggestion);
      //setSelectedTopic(suggestion);
      console.log("searchedTopic:", searchedTopic); //suggestion print
      setQuery(suggestion);
      setSuggestions([]);
      setShowSuggestions(false);
    };

  const handleClickOutside = (event) => {
    if (
      suggestionsRef.current &&
      !suggestionsRef.current.contains(event.target)
    ) {
      setShowSuggestions(false);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <div
      className="search-component"
      style={{ position: "relative", width: "300px" }}
    >
      <input
        type="text"
        value={query}
        onChange={handleInputChange}
        placeholder="Search..."
        style={{ width: "100%", padding: "8px", boxSizing: "border-box" }}
      />
      {isLoading && <div>Loading...</div>}
      {showSuggestions && suggestions.length > 0 && (
        <ul
          ref={suggestionsRef}
          style={{
            position: "absolute",
            top: "100%",
            left: 0,
            right: 0,
            border: "1px solid #ccc",
            backgroundColor: "#fff",
            listStyleType: "none",
            margin: 0,
            padding: 0,
            maxHeight: "150px",
            overflowY: "auto",
            zIndex: 1,
          }}
        >
          {suggestions.map((suggestion, index) => (
            <li
              key={index}
              onClick={() => handleSuggestionClick(suggestion)}
              style={{
                padding: "8px",
                cursor: "pointer",
                borderBottom: "1px solid #ddd",
              }}
            >
              {suggestion}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default SearchBox;
