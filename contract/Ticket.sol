// SPDX-License-Identifier: MIT
pragma solidity >=0.8.2 <0.9.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "Rendering_T.sol";
import "RenderingParams_T.sol";

contract Ticket is ERC721Enumerable, Ownable {
    uint public total;
    string public initial;
    string public email;

    // 입장 이벤트
    event Entered(uint indexed _tokenId);

    constructor() ERC721("HappyTicket", "HTK") Ownable(msg.sender) {}

    // 초기 구매자 등록
    function fill(string memory _initial, string memory _email) public {
        initial = _initial;
        email = _email;
    }

    // 티켓 발급
    function mintTicket() external onlyOwner {
        total++;
        uint tokenId = total;
        _mint(msg.sender, tokenId);
    }

    // 입장
    function enter(uint _tokenId) external onlyOwner {
        emit Entered(_tokenId);
        _burn(_tokenId);
    }

    // NFT Renderer library 사용하여 메타데이터 생성
    function tokenURI(uint _tokenId) public view override returns (string memory) {
        RenderParams_T memory params = RenderParams_T({
            musician: "BTS",
            title: "PERMISSION TO DANCE ON STAGE",
            initial: initial,
            email: email,
            location: "Seoul Olympic Main Stadium",
            date: "Sunday, May 12, 2024"
        });

        return NFTRenderer_T.render(params);
    }
}
