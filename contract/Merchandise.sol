// SPDX-License-Identifier: MIT
pragma solidity >=0.8.2 <0.9.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "Rendering_M.sol";
import "RenderingParams_M.sol";

contract Merchandise is ERC721Enumerable, Ownable {
    // 최초구매자 리스트 (tokenId => bool)
    mapping(uint => bool) internal isFirstOwner;

    constructor() ERC721("HappyMerch", "HMC") Ownable(msg.sender) {}

    // 수집용 굿즈 NFT 발급, admin만 실행 가능
    function mintMerch(address _addr) public onlyOwner {
        uint _tokenId = totalSupply() + 1;
        _mint(_addr, _tokenId);
    }

    // 백엔드에서 input값 받아서 최초구매자 상태 업데이트 기능
    // _tokenId가 input값이기 때문에 민팅이 먼저 실행돼야 함
    function isFirst(uint _tokenId, bool _isFirst) external onlyOwner {
        isFirstOwner[_tokenId] = _isFirst;
    }

    // 최초 구매자인지 아닌지 구분, 상품성 있는 URI / 상품성 없는 URI
    function tokenURI(uint _tokenId) public view override returns (string memory) {
        RenderParams_M memory params = RenderParams_M({
            musician: "BTS",
            title: "PERMISSION TO DANCE ON STAGE",
            location: "Seoul Olympic Main Stadium",
            date: "Sunday, May 12, 2024"
        });

        if(isFirstOwner[_tokenId]) {
            return NFTRenderer_M_Good.render(params);
        }
        else {
            return NFTRenderer_M_Bad.render(params);
        }
        
    }
}