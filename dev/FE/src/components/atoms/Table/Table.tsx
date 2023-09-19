import { useMemo } from 'react';
// import dummy from './dummy.json';
import { usePagination, useTable } from 'react-table';
import './Table.module.scss';
import { snakeToTitle } from '@/utils';

// const cols = Object.keys(dummy[0]).map((snake) => {
//   return {
//     Header: snakeToTitle(snake),
//     accessor: snake,
//   };
// });

import { convertClassName, convertClassNameList } from '@/utils';
import styles from './Table.module.scss';

interface TableProps {
  className?: string;
  data?: any;
}

const Table = ({ className, data }: TableProps): JSX.Element => {
  const columns = useMemo(() => {
    Object.keys(data?.[0]).map((snake) => {
      return {
        Header: snakeToTitle(snake),
        accessor: snake,
      };
    });
  }, []);
  // const data = useMemo(() => dummy, []);
  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    page,
    nextPage,
    previousPage,
    canNextPage,
    canPreviousPage,
    pageOptions,
    gotoPage,
    pageCount,
    setPageSize,
    state,
    prepareRow,
  } = useTable(
    {
      // @ts-ignore
      columns,
      data,
    },
    usePagination,
  );
  const { pageIndex, pageSize } = state;
  return (
    <div
      className={convertClassNameList(
        convertClassName(className, styles),
        'table',
      )}
    >
      <table {...getTableProps()}>
        <thead>
          {headerGroups.map((headerGroup) => (
            <tr {...headerGroup.getHeaderGroupProps()}>
              {headerGroup.headers.map((column) => (
                <th
                  style={{ textAlign: 'center' }}
                  {...column.getHeaderProps()}
                >
                  {column.render('Header')}
                </th>
              ))}
            </tr>
          ))}
        </thead>

        <tbody {...getTableBodyProps()}>
          {page.map((row: any) => {
            prepareRow(row);
            return (
              <tr {...row.getRowProps()}>
                {row.cells.map((cell: any) => {
                  return (
                    <td
                      style={{ textAlign: 'center' }}
                      {...cell.getCellProps()}
                    >
                      {cell.render('Cell')}
                    </td>
                  );
                })}
              </tr>
            );
          })}
        </tbody>
      </table>

      <div
        className="table-pagination"
        style={{
          margin: '5px',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <button onClick={() => gotoPage(0)} disabled={!canPreviousPage}>
          {'<<'}
        </button>
        <button onClick={() => previousPage()} disabled={!canPreviousPage}>
          이전
        </button>
        <span>
          <strong
            style={{ display: 'block', width: '100px', textAlign: 'center' }}
          >
            {pageIndex + 1} / {pageOptions.length}
          </strong>
        </span>
        {/* <span>
          Go to page:{' '}
          <input
            type="number"
            defaultValue={pageIndex + 1}
            onChange={(e) => {
              const pageNumber = e.target.value
                ? Number(e.target.value) - 1
                : 0;
              gotoPage(pageNumber);
            }}
            style={{ width: '50px' }}
          />
        </span> */}
        <button onClick={() => nextPage()} disabled={!canNextPage}>
          다음
        </button>
        <button onClick={() => gotoPage(pageCount - 1)} disabled={!canNextPage}>
          {'>>'}
        </button>
      </div>
      <div
        className="table-pagesize"
        style={{
          margin: '5px',
          display: 'flex',
          justifyContent: 'flex-end',
          alignItems: 'center',
        }}
      >
        <select
          value={pageSize}
          onChange={(e) => setPageSize(Number(e.target.value))}
        >
          {[10, 25, 50].map((pageSize) => (
            <option key={pageSize} value={pageSize}>
              {pageSize}개 씩 보기
            </option>
          ))}
        </select>
      </div>
    </div>
  );
};

export default Table;
