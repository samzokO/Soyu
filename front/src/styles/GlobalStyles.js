import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';
import './font.css';

const GlobalStyles = createGlobalStyle`
    ${reset}
    *{margin:0;padding:0;font:inherit;color:inherit;}
    *, :after, :before {box-sizing:border-box;}
    :root {-webkit-tap-highlight-color:transparent;-webkit-text-size-adjust:100%;text-size-adjust:100%;cursor:default;line-height:1.5;overflow-wrap:break-word;word-break:break-word;tab-size:4}
    html, body {background-color: ${(props) => props.theme.bgColor}; font-family: 'Pretendard'; font-weight: 600;}
    img, picture, video, canvas, svg {display: block;max-width:100%;}
    button {background:none;border:0;cursor:pointer;}
    a {text-decoration:none}
    table {border-collapse:collapse;border-spacing:0}
`;

export default GlobalStyles;
