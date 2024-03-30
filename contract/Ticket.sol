// SPDX-License-Identifier: MIT
pragma solidity >=0.8.2 <0.9.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/token/ERC721/IERC721.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract Ticket is ERC721Enumerable, Ownable {
    string musician;
    string metadataURI;
    uint total;

    // 콘서트리스트
    mapping(string => uint) internal concertNum;

    // 입장 이벤트
    event Entered(uint indexed _tokenId);

    // 주최자의 의뢰대로 인풋값 입력, admin = contract 배포자
    constructor(string memory _name, string memory _symbol, string memory _metadataURI) ERC721(_name, _symbol) Ownable(msg.sender) {
        musician = _name;
        metadataURI = _metadataURI;
    }

    // 티켓 발급 : admin만 원하는 address로 민팅, 가스비 사용하므로 admin만 실행 가능
    function mintTicket(address _addr) external onlyOwner {
        total++;
        uint tokenId = total;
        _mint(_addr, tokenId);
    }
    
    // 입장
    function enter(uint _tokenId) external onlyOwner {
        emit Entered(_tokenId);
        _burn(_tokenId);
    }

    function tokenURI(uint _tokenId) public view override returns(string memory) {
        return string(abi.encodePacked(metadataURI, '/', musician, '.json'));
    }
}