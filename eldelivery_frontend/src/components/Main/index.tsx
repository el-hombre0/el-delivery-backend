import useGeolocation from "../../hooks/useGeolocation";
export const Main = () => {
  const location = useGeolocation();
  return (
    <>
      <h1>Главная страница</h1>
      {location.loaded
        ? JSON.stringify(location)
        : "Location data is not available yet."}
    </>
  );
};
