import React from "react";
import TextField from "@mui/material/TextField";
import "./CurrentWeatherPage.css";
import Button from "@mui/material/Button";
import { styled } from "@mui/material/styles";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));

function createData(name: string, data: string) {
  return { name, data };
}

const rows = [
  createData("Weather for", "city"),
  createData("Date", "date"),
  createData("Preceived Temperature", "precTemp"),
  createData("Temperature", "temp"),
  createData("Condition", "cond"),
];

export const CurrentWeatherPage = () => {
  return (
    <div className="main">
      <div className="inputForm">
        <p>Choose city</p>
        <TextField id="outlined-basic" label="City" variant="outlined" />
      </div>
      <div className="searchBtn">
        <Button variant="contained">Search</Button>
      </div>
      <div className="displayForm">
        <TableContainer className="tablecoint" component={Paper}>
          <Table aria-label="customized table">
            <TableBody>
              {rows.map((row) => (
                <StyledTableRow key={row.name}>
                  <StyledTableCell component="th" scope="row">
                    {row.name}
                  </StyledTableCell>
                  <StyledTableCell align="center">{row.data}</StyledTableCell>
                </StyledTableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </div>
    </div>
  );
};

export default CurrentWeatherPage;
