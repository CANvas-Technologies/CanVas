const express = require("express");
const cors = require("cors");

const app = express();
app.use(cors());
app.use(express.json());

app.put("/api/data", (req, res) => {
  console.log(req.body);
  res.json({ message: "Data updated successfully" });
});

app.listen(3001, () => {
  console.log("Server listening on port 3001");
});
