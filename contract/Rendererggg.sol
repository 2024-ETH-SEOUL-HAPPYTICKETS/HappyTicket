import "@openzeppelin/contracts/utils/Base64.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

library NFTRenderer {
    struct RenderParams {
        string musician;
        string title;
        string initial;
        string location;
        string date;
    }

    function render(RenderParams memory params){
        string memory image = string.concat(
            '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 300 480">',
            "<style>.musician {font: bold 30px sans-serif; }",
            ".title {font: normal 15px sans-serif; }",
            ".details {font: normal 14px sans-serif; }",
            ".initial {font: normal 14px sans-serif; }</style>",
            renderBackground(params.musician, params.title),
            renderTop(params.musician, params.title),
            renderBottom(params.initial, params.location, params.date),
            "</svg>"
        );

        string memory description = renderDescription(
            params.musician,
            params.title,
            params.initial
        );

        string memory json = string.concat(
            '{"name":"',
            params.musician,
            '",',
            '"description":"',
            description,
            '",',
            '"image":"data:image/svg+xml;base64,',
            Base64.encode(bytes(image)),
            '"}'
        );

        return
            string.concat(
                "data:application/json;base64,",
                Base64.encode(bytes(json))
            );
    }

    function renderBackground(string memory musician, string memory title) internal pure returns(string memory background) {
        bytes32 key = keccak256(abi.encodePacked(musician, title));
        uint hue = uint(key) % 360;

        background = string.concat(
            '<rect width="300" height="480" fill="hsl(',
            String.toString(hue),
            '40%,40%)" />',
            '<rect x="30" y="30" width="240" height="420" rx="15" ry="15" fill="hsl('
            String.toString(hue),
            '90%,50%)" stroke="#000"/>',
        );
    }

    function renderTop(string memory musician, string memory title) internal pure returns(string memory top) {
        top = string.concat(
            '<rect x="30" y="87" width="240" height="42" />',
            '<text x="39" y="120" class="musician" fill="#fff">',
            musician,
            "</text>",
            '<rect x="30" y="132" width="240" height="30" />',
            '<text x="39" y="120" dy="33" class="title" fill="#fff">',
            title,
            "</text>"
        );
    }

    function renderBottom(string memory initial, string memory location, string memory date) internal pure returns(string memory bottom) {
        bottom = string.concat(
            '<rect x="30" y="240" width="240" height="30" />',
            '<text x="75" y="225" dy="35" class="initial" fill="#fff">',
            initial,
            " | ",
            email,
            "</text>",
            '<rect x="30" y="342" width="240" height="24" />',
            '<text x="39" y="360" class="location" fill="#fff">Location: ',
            location,
            "</text>",
            '<rect x="30" y="372" width="240" height="24" />',
            '<text x="39" y="360" dy="30" class="date" fill="#fff">Date: ',
            date,
            "</text>"
        );
    }

    function renderDescription(string musician, string title, string location, string date) internal pure returns(string memory description) {
        description = string.concat(
            musician,
            title,
            "Location: ",
            location,
            "Date: ",
            date
        );
    }

    function tokenURI(uint _tokenId) public view override returns(string memory) {
        return
            NFTRenderer.render(
                NFTRenderer.RenderParams({
                    musician: ,
                    title: ,
                    initial: ,
                    location: ,
                    date: 
                })
            );
    }
}
