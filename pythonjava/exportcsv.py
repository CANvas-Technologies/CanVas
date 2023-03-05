import os
import sys
import uuid

from asammdf import MDF, Signal

def main():
    input_mdf = sys.argv[1]
    _input_dbc = sys.argv[2] # currently unused

    mdf = MDF(input_mdf)

    unique_id = uuid.uuid4()
    dir = f'out_{unique_id}'
    os.mkdir(dir)

    mdf.export('csv', f'out_{unique_id}/out.csv')

    print(dir)


if __name__ == '__main__':
    main()
