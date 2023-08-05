export default function SearchBar(props) {
  const handleSubmit = (e) => {
    e.preventDefault();
    props.handleSearchSubmit(e.target[0].value);
  };
  return (
    <form className="w-72 border rounded-2xl ml-10" onSubmit={handleSubmit}>
      <input type="text" placeholder="Ara..." className="p-2 w-full bg-job-posts-background rounded-2xl" />
    </form>
  );
}
