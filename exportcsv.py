from sys import stdout
from asammdf import MDF, Signal


def main():
    # input_mdf = "mf41.MF4"
    # input_dbc = "testdbc.dbc"
    input_mdf = sys.argv[1]
    input_dbc = sys.argv[2]
    print(input_mdf)
    print(input_dbc)
    mdf = MDF(input_mdf)
    databases = {"CAN": [(input_dbc, 1)]}
    extracted = mdf.extract_bus_logging(database_files=databases)
    speed = extracted.get("EngineSpeed")
    extracted.export('csv', "out.csv")


if __name__ == '__main__':
    main()
