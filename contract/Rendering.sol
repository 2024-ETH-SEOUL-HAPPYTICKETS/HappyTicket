// SPDX-License-Identifier: MIT
pragma solidity >=0.8.2 <0.9.0;

import "@openzeppelin/contracts/utils/Base64.sol";
import "@openzeppelin/contracts/utils/Strings.sol";
import "RenderingParams.sol";

library NFTRenderer {
    using Strings for uint256;

    function render(RenderParams memory params) internal pure returns (string memory) {
        string memory image = generateSVG(params);

        string memory json = string(
            abi.encodePacked(
                '{"name":"',
                params.musician,
                '","description":"',
                params.musician,
                ' ',
                params.title,
                '","image":"data:image/svg+xml;base64,',
                Base64.encode(bytes(image)),
                '","attributes":['
            )
        );

        // Add attributes to JSON
        json = string(
            abi.encodePacked(
                json,
                '{"trait_type":"Title","value":"',
                params.title,
                '"},',
                '{"trait_type":"Name","value":"',
                params.initial,
                '"},',
                '{"trait_type":"Email","value":"',
                params.email,
                '"},',
                '{"trait_type":"Location","value":"',
                params.location,
                '"},',
                '{"trait_type":"Date","value":"',
                params.date,
                '"}'
            )
        );

        // Close JSON object
        json = string(abi.encodePacked(json, ']}'));
        
        return string(
            abi.encodePacked(
                "data:application/json;base64,",
                Base64.encode(bytes(json))
            )
        );
    }

    function generateSVG(RenderParams memory params) internal pure returns (string memory) {
        string memory svg = string(
            abi.encodePacked(
                '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 300 480">',
                '<style>',
                '.musician { font: bold 30px sans-serif; }',
                '.title { font: normal 15px sans-serif; }',
                '.initial { font: normal 14px sans-serif; }',
                '.location { font: normal 14px sans-serif; }',
                '.date { font: normal 14px sans-serif; }',
                '</style>',
                '<rect width="300" height="480" fill="hsl(330,40%,40%)" />',
                '<rect x="30" y="30" width="240" height="420" rx="15" ry="15" fill="hsl(330,90%,50%)" stroke="#000" />',
                '<text x="39" y="120" class="musician" fill="#fff">',
                params.musician,
                '</text>',
                '<text x="39" y="165" class="title" fill="#fff">',
                params.title,
                '</text>',
                '<text x="75" y="225" class="initial" fill="#fff">',
                params.initial,
                ' | ',
                params.email,
                '</text>',
                '<text x="39" y="375" class="location" fill="#fff">',
                'Location: ',
                params.location,
                '</text>',
                '<text x="39" y="405" class="date" fill="#fff">',
                'Date: ',
                params.date,
                '</text>',
                '</svg>'
            )
        );

        return svg;
    }
}
